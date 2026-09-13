package com.myAgeEducation.cbseClass7.MathTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MathTextView
 *
 * Supports:
 *  - Plain text without WebView overhead
 *  - Mixed text + LaTeX
 *  - Inline math:     $x^2$
 *  - Display math:    $$\\frac{1}{2}$$
 *  - \( ... \)
 *  - \[ ... \]
 *  - Dynamic WebView height
 *  - RecyclerView reuse
 *  - Dark/light/auto theme
 *  - Configurable font size
 *  - Clickable HTTP/HTTPS links
 *  - WebView renderer recovery
 *  - Explicit lifecycle cleanup
 *
 * Important:
 * Call release() when the owning Activity/Fragment/ViewHolder is permanently
 * destroyed and this view will no longer be reused.
 */
public class MathTextView extends FrameLayout {

    // -------------------------------------------------------------------------
    // Public types
    // -------------------------------------------------------------------------

    public enum ThemeMode {
        AUTO,
        LIGHT,
        DARK
    }

    public interface OnLinkClickListener {
        /**
         * Return true if the app handled the URL itself.
         * Return false to let MathTextView open it externally.
         */
        boolean onLinkClick(@NonNull String url);
    }

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final String BASE_URL =
            "file:///android_asset/katex/";

    private static final float DEFAULT_FONT_SIZE_SP = 18f;

    private static final int DEFAULT_TEXT_COLOR_LIGHT =
            Color.rgb(25, 25, 25);

    private static final int DEFAULT_TEXT_COLOR_DARK =
            Color.rgb(235, 235, 235);

    private static final int DEFAULT_LINK_COLOR_LIGHT =
            Color.rgb(20, 90, 180);

    private static final int DEFAULT_LINK_COLOR_DARK =
            Color.rgb(110, 170, 255);

    /*
     * We detect LaTeX before deciding whether to use WebView.
     *
     * Matches:
     *   $...$
     *   $$...$$
     *   \( ... \)
     *   \[ ... \]
     */
    private static final Pattern MATH_PATTERN = Pattern.compile(
            "(\\$\\$[\\s\\S]*?\\$\\$)" +
                    "|" +
                    "(?<!\\$)\\$(?!\\$)[\\s\\S]*?(?<!\\$)\\$(?!\\$)" +
                    "|" +
                    "(\\\\\\([\\s\\S]*?\\\\\\))" +
                    "|" +
                    "(\\\\\\[[\\s\\S]*?\\\\\\])"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
            "(https?://[^\\s<]+)",
            Pattern.CASE_INSENSITIVE
    );

    // -------------------------------------------------------------------------
    // Views
    // -------------------------------------------------------------------------

    private TextView plainTextView;

    @Nullable
    private WebView webView;

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private String currentText = "";

    private float fontSizeSp = DEFAULT_FONT_SIZE_SP;

    private ThemeMode themeMode = ThemeMode.LIGHT;

    @Nullable
    private Integer customLightTextColor;

    @Nullable
    private Integer customDarkTextColor;

    @Nullable
    private Integer customLightLinkColor;

    @Nullable
    private Integer customDarkLinkColor;

    private boolean textSelectable = true;

    private boolean released = false;

    private boolean attachedToWindow = false;

    @Nullable
    private OnLinkClickListener onLinkClickListener;

    /*
     * Protects against obsolete asynchronous resize callbacks when a
     * RecyclerView rebinds this view quickly.
     */
    private long renderGeneration = 0;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public MathTextView(@NonNull Context context) {
        this(context, null);
    }

    public MathTextView(
            @NonNull Context context,
            @Nullable AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public MathTextView(
            @NonNull Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        setClipChildren(false);
        setClipToPadding(false);

        createPlainTextView();
    }

    // -------------------------------------------------------------------------
    // Initial setup
    // -------------------------------------------------------------------------

    private void createPlainTextView() {

        plainTextView = new TextView(getContext());

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );

        plainTextView.setLayoutParams(params);

        plainTextView.setTextSize(fontSizeSp);
        plainTextView.setTextIsSelectable(textSelectable);

        plainTextView.setLineSpacing(0f, 1.15f);

        plainTextView.setMovementMethod(
                LinkMovementMethod.getInstance()
        );

        addView(plainTextView);

        updatePlainTextTheme();
    }

    @SuppressLint({
            "SetJavaScriptEnabled",
            "AddJavascriptInterface"
    })
    private void createWebView() {

        if (released || webView != null) {
            return;
        }

        WebView newWebView = new WebView(getContext());

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                1
        );

        newWebView.setLayoutParams(params);

        newWebView.setBackgroundColor(Color.TRANSPARENT);

        newWebView.setVerticalScrollBarEnabled(false);
        newWebView.setHorizontalScrollBarEnabled(false);

        newWebView.setOverScrollMode(OVER_SCROLL_NEVER);

        newWebView.setLongClickable(textSelectable);

        WebSettings settings = newWebView.getSettings();

        // Required for KaTeX.
        settings.setJavaScriptEnabled(true);

        // Local KaTeX assets.
        settings.setAllowFileAccess(true);

        // We don't need content:// access.
        settings.setAllowContentAccess(false);

        /*
         * Prevent local file pages from accessing arbitrary remote/file
         * resources.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }

        /*
         * MathTextView itself never needs network resources.
         * Links are opened externally instead.
         */
        settings.setBlockNetworkLoads(true);

        settings.setDomStorageEnabled(false);
        settings.setDatabaseEnabled(false);

        settings.setLoadsImagesAutomatically(true);

        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        settings.setTextZoom(100);

        settings.setDefaultTextEncodingName("UTF-8");

        /*
         * Prevent accidental horizontal scaling.
         */
        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(false);

        newWebView.addJavascriptInterface(
                new ResizeJavascriptBridge(),
                "AndroidMathBridge"
        );

        newWebView.setWebViewClient(
                createWebViewClient()
        );

        webView = newWebView;

        addView(
                newWebView,
                new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        1
                )
        );

        if (attachedToWindow) {
            newWebView.onResume();
        }
    }

    // -------------------------------------------------------------------------
    // Main API
    // -------------------------------------------------------------------------

    /**
     * Set normal or mathematical text.
     *
     * Example:
     *
     *   "Calculate $\\frac{3}{4} + \\frac{1}{2}$."
     */
    public void setText(@Nullable String text) {

        if (released) {
            return;
        }

        currentText = text == null ? "" : text.trim();

        renderGeneration++;

        if (containsMath(currentText)) {

            showMathContent();

        } else {

            showPlainText();
        }
    }

    @NonNull
    public String getText() {
        return currentText;
    }

    public void setFontSizeSp(float fontSizeSp) {

        if (fontSizeSp <= 0f) {
            return;
        }

        this.fontSizeSp = fontSizeSp;

        plainTextView.setTextSize(fontSizeSp);

        rerender();
    }

    public float getFontSizeSp() {
        return fontSizeSp;
    }

    public void setThemeMode(
            @NonNull ThemeMode themeMode) {

        this.themeMode = themeMode;

        updatePlainTextTheme();

        rerender();
    }

    @NonNull
    public ThemeMode getThemeMode() {
        return themeMode;
    }

    public void setTextSelectable(boolean selectable) {

        textSelectable = selectable;

        plainTextView.setTextIsSelectable(selectable);

        if (webView != null) {
            webView.setLongClickable(selectable);
        }
    }

    public void setOnLinkClickListener(
            @Nullable OnLinkClickListener listener) {

        this.onLinkClickListener = listener;
    }

    // -------------------------------------------------------------------------
    // Optional color customization
    // -------------------------------------------------------------------------

    public void setLightTextColor(int color) {
        customLightTextColor = color;
        updatePlainTextTheme();
        rerender();
    }

    public void setDarkTextColor(int color) {
        customDarkTextColor = color;
        updatePlainTextTheme();
        rerender();
    }

    public void setLightLinkColor(int color) {
        customLightLinkColor = color;
        updatePlainTextTheme();
        rerender();
    }

    public void setDarkLinkColor(int color) {
        customDarkLinkColor = color;
        updatePlainTextTheme();
        rerender();
    }

    // -------------------------------------------------------------------------
    // Rendering
    // -------------------------------------------------------------------------

    private void rerender() {

        if (released || currentText == null) {
            return;
        }

        setText(currentText);
    }

    private void showPlainText() {

        if (webView != null) {

            webView.stopLoading();
            webView.setVisibility(GONE);
        }

        plainTextView.setVisibility(VISIBLE);

        updatePlainTextTheme();

        SpannableString spannable =
                new SpannableString(currentText);

        Linkify.addLinks(
                spannable,
                Linkify.WEB_URLS
        );

        plainTextView.setText(spannable);
    }

    private void showMathContent() {

        plainTextView.setVisibility(GONE);

        createWebView();

        if (webView == null) {
            return;
        }

        webView.setVisibility(VISIBLE);

        /*
         * Keep the old row's height from flashing during RecyclerView binding.
         */
        ViewGroup.LayoutParams params =
                webView.getLayoutParams();

        if (params.height <= 0) {
            params.height = 1;
            webView.setLayoutParams(params);
        }

        long generation = renderGeneration;


        String html = buildHtml(
                currentText,
                generation
        );

        webView.loadDataWithBaseURL(
                BASE_URL,
                html,
                "text/html",
                "UTF-8",
                null
        );
    }

    private int getAvailableContentWidth() {
        return Math.max(
                1,
                getMeasuredWidth()
                        - getPaddingLeft()
                        - getPaddingRight()
        );
    }

    // -------------------------------------------------------------------------
    // HTML
    // -------------------------------------------------------------------------

    private String buildHtml(
            @NonNull String text,
            long generation
            ) {

        boolean dark = shouldUseDarkTheme();

        int textColor = dark
                ? getDarkTextColor()
                : getLightTextColor();

        int linkColor = dark
                ? getDarkLinkColor()
                : getLightLinkColor();

        /*
         * CSS pixels approximately correspond to dp under the viewport meta
         * tag. Convert SP to density-independent CSS px while respecting
         * Android font scaling.
         */
        DisplayMetrics dm =
                getResources().getDisplayMetrics();

        float cssFontSize =
                fontSizeSp * dm.scaledDensity / dm.density;

        String htmlContent =
                convertPlainTextToSafeHtml(text);


        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"

                + "<meta charset=\"UTF-8\">"

                + "<meta name=\"viewport\" "
                + "content=\"width=device-width,"
                + " initial-scale=1.0,"
                + " maximum-scale=1.0,"
                + " user-scalable=no\">"

                + "<link rel=\"stylesheet\" "
                + "href=\"katex.min.css\">"

                + "<script defer "
                + "src=\"katex.min.js\"></script>"

                + "<script defer "
                + "src=\"contrib/auto-render.min.js\"></script>"

                + "<style>"

                + "*{"
                + "box-sizing:border-box;"
                + "}"

                + "html{"
                + "margin:0;"
                + "padding:0;"
                + "width:100%;"
                + "max-width:100%;"
                + "overflow-x:hidden;"
                + "}"

                + "body{"
                + "margin:0;"
                + "padding:0;"
                + "width:100%;"
                + "max-width:100%;"
                + "font-family:sans-serif;"
                + "font-size:"
                + formatFloat(cssFontSize)
                + "px;"
                + "line-height:1.35;"
                + "color:"
                + colorToCss(textColor)
                + ";"
                + "overflow-x:hidden;"
                + "overflow-wrap:anywhere;"
                + "word-wrap:break-word;"
                + "}"

                + "#math-content{"
                + "display:block;"
                + "width:100%;"
                + "max-width:100%;"
                + "min-width:0;"
                + "margin:0;"
                + "padding:0;"
                + "overflow-wrap:anywhere;"
                + "word-wrap:break-word;"
                + "}"

                + "img,svg{"
                + "max-width:100%;"
                + "height:auto;"
                + "}"

                + ".katex-display{"
                + "display:block;"
                + "max-width:100%;"
                + "overflow-x:auto;"
                + "overflow-y:hidden;"
                + "margin:0.55em 0;"
                + "padding:2px 0;"
                + "}"

                + ".katex{"
                + "white-space:normal;"
                + "overflow-wrap:anywhere;"
                + "word-break:normal;"
                + "}"

                + ".katex-display > .katex{"
                + "white-space:nowrap;"
                + "}"

                + "</style>"

                + "</head>"

                + "<body>"

                + "<div id=\"math-content\">"
                + htmlContent
                + "</div>"

                + "<script>"

                + "const renderGeneration="
                + generation
                + ";"

                + "function reportHeight(){"
                + "  try {"
                + "    const el=document.getElementById('math-content');"
                + "    if(!el) return;"
                + "    const h=el.getBoundingClientRect().height;"
                + "    AndroidMathBridge.reportHeight("
                + "      h,"
                + "      renderGeneration"
                + "    );"
                + "  } catch(e) {}"
                + "}"

                + "document.addEventListener("
                + "'DOMContentLoaded',"
                + "function(){"

                + "  try {"

                + "    renderMathInElement("
                + "      document.getElementById('math-content'),"
                + "      {"
                + "        delimiters:["
                + "          {left:'$$',right:'$$',display:true},"
                + "          {left:'\\\\[',right:'\\\\]',display:true},"
                + "          {left:'\\\\(',right:'\\\\)',display:false},"
                + "          {left:'$',right:'$',display:false}"
                + "        ],"
                + "        throwOnError:false,"
                + "        strict:false"
                + "      }"
                + "    );"

                + "  } catch(e) {}"

                /*
                 * ResizeObserver catches font loading, KaTeX rendering and
                 * layout changes.
                 */
                + "  if (window.ResizeObserver) {"
                + "    const observer=new ResizeObserver(function(){"
                + "      reportHeight();"
                + "    });"
                + "    observer.observe("
                + "      document.getElementById('math-content')"
                + "    );"
                + "  }"

                + "  reportHeight();"
                + "  setTimeout(reportHeight,30);"
                + "  setTimeout(reportHeight,100);"
                + "});"

                + "</script>"

                + "</body>"
                + "</html>";
    }

    /**
     * Treat question-bank content as plain text, not trusted HTML.
     *
     * This:
     *  - escapes HTML
     *  - preserves line breaks
     *  - turns HTTP/HTTPS URLs into links
     *  - leaves LaTeX delimiters intact
     */
    private String convertPlainTextToSafeHtml(
            @NonNull String text) {

        StringBuilder result = new StringBuilder();

        Matcher matcher = URL_PATTERN.matcher(text);

        int last = 0;

        while (matcher.find()) {

            String before =
                    text.substring(
                            last,
                            matcher.start()
                    );

            result.append(
                    escapeHtml(before)
                            .replace("\n", "<br>")
            );

            String url = matcher.group(1);

            result.append("<a href=\"")
                    .append(escapeHtmlAttribute(url))
                    .append("\">")
                    .append(escapeHtml(url))
                    .append("</a>");

            last = matcher.end();
        }

        String remainder =
                text.substring(last);

        result.append(
                escapeHtml(remainder)
                        .replace("\n", "<br>")
        );

        return result.toString();
    }

    // -------------------------------------------------------------------------
    // Dynamic height bridge
    // -------------------------------------------------------------------------

    private final class ResizeJavascriptBridge {

        @JavascriptInterface
        public void reportHeight(
                final double cssHeight,
                final long generation) {

            /*
             * JavascriptInterface calls are not guaranteed to execute on the
             * UI thread.
             */
            post(() -> {

                if (released) {
                    return;
                }

                if (generation != renderGeneration) {

                    /*
                     * RecyclerView has rebound this view since JavaScript sent
                     * the callback.
                     */
                    return;
                }

                if (webView == null) {
                    return;
                }

                float density =
                        getResources()
                                .getDisplayMetrics()
                                .density;

                int pixelHeight =
                        Math.max(
                                1,
                                (int) Math.ceil(
                                        cssHeight * density
                                )
                        );

                ViewGroup.LayoutParams params =
                        webView.getLayoutParams();

                if (params.height != pixelHeight) {

                    params.height = pixelHeight;

                    webView.setLayoutParams(params);
                }
            });
        }
    }

    // -------------------------------------------------------------------------
    // WebViewClient
    // -------------------------------------------------------------------------

    private WebViewClient createWebViewClient() {

        return new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request) {

                return handleUri(
                        request.getUrl()
                );
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    String url) {

                return handleUri(
                        Uri.parse(url)
                );
            }

            @Override
            public boolean onRenderProcessGone(
                    WebView view,
                    RenderProcessGoneDetail detail) {

                /*
                 * Android says a WebView whose renderer died must not be
                 * reused.
                 */
                handleRendererGone(view);

                return true;
            }
        };
    }

    private boolean handleUri(
            @Nullable Uri uri) {

        if (uri == null) {
            return true;
        }

        String scheme = uri.getScheme();

        if (scheme == null) {
            return true;
        }

        if (!scheme.equalsIgnoreCase("http")
                && !scheme.equalsIgnoreCase("https")) {

            /*
             * Do not allow arbitrary URI schemes from question content.
             */
            return true;
        }

        String url = uri.toString();

        if (onLinkClickListener != null) {

            try {

                if (onLinkClickListener
                        .onLinkClick(url)) {

                    return true;
                }

            } catch (Exception ignored) {
            }
        }

        openExternalUrl(uri);

        return true;
    }

    private void openExternalUrl(
            @NonNull Uri uri) {

        try {

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            uri
                    );

            Context context = getContext();

            if (!(context instanceof Activity)) {

                intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                );
            }

            context.startActivity(intent);

        } catch (Exception ignored) {

            /*
             * No browser / matching Activity.
             *
             * In a production app you could notify the user here through your
             * own listener or Toast.
             */
        }
    }

    // -------------------------------------------------------------------------
    // Renderer recovery
    // -------------------------------------------------------------------------

    private void handleRendererGone(
            @NonNull WebView deadWebView) {

        if (webView != deadWebView) {
            return;
        }

        removeAndDestroyWebView(deadWebView);

        webView = null;

        if (!released
                && containsMath(currentText)) {

            post(this::showMathContent);
        }
    }

    // -------------------------------------------------------------------------
    // Theme handling
    // -------------------------------------------------------------------------

    private boolean shouldUseDarkTheme() {

        if (themeMode == ThemeMode.DARK) {
            return true;
        }

        if (themeMode == ThemeMode.LIGHT) {
            return false;
        }

        int mode =
                getResources()
                        .getConfiguration()
                        .uiMode
                        & Configuration.UI_MODE_NIGHT_MASK;

        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    private void updatePlainTextTheme() {

        boolean dark = shouldUseDarkTheme();

        int textColor =
                dark
                        ? getDarkTextColor()
                        : getLightTextColor();

        int linkColor =
                dark
                        ? getDarkLinkColor()
                        : getLightLinkColor();

        plainTextView.setTextColor(textColor);
        plainTextView.setLinkTextColor(linkColor);

        /*
         * Transparent so the parent/card controls the actual background.
         */
        plainTextView.setBackgroundColor(
                Color.TRANSPARENT
        );
    }

    private int getLightTextColor() {

        return customLightTextColor != null
                ? customLightTextColor
                : DEFAULT_TEXT_COLOR_LIGHT;
    }

    private int getDarkTextColor() {

        return customDarkTextColor != null
                ? customDarkTextColor
                : DEFAULT_TEXT_COLOR_DARK;
    }

    private int getLightLinkColor() {

        return customLightLinkColor != null
                ? customLightLinkColor
                : DEFAULT_LINK_COLOR_LIGHT;
    }

    private int getDarkLinkColor() {

        return customDarkLinkColor != null
                ? customDarkLinkColor
                : DEFAULT_LINK_COLOR_DARK;
    }

    // -------------------------------------------------------------------------
    // RecyclerView support
    // -------------------------------------------------------------------------

    /**
     * Call from RecyclerView.ViewHolder when the row is recycled.
     *
     * This does NOT destroy the WebView because the same ViewHolder may be
     * rebound immediately.
     */
    public void recycle() {

        if (released) {
            return;
        }

        renderGeneration++;

        currentText = "";

        plainTextView.setText("");
        plainTextView.setVisibility(VISIBLE);

        if (webView != null) {

            webView.stopLoading();

            /*
             * about:blank is app-initiated and clears the previous DOM.
             */
            webView.loadUrl("about:blank");

            webView.setVisibility(GONE);

            ViewGroup.LayoutParams params =
                    webView.getLayoutParams();

            params.height = 1;

            webView.setLayoutParams(params);
        }
    }

    /**
     * Optional helper for ViewHolder binding.
     */
    public void bind(@Nullable String text) {
        setText(text);
    }

// -------------------------------------------------------------------------
// Android View lifecycle
// -------------------------------------------------------------------------

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * If the MathTextView itself is set to be clickable (e.g. via android:onClick
         * in XML), we want it to handle the touch events rather than its internal
         * WebView or TextView stealing them.
         *
         * This allows MathTextView to behave like a Button when needed, while
         * still allowing the question text (which is usually not clickable)
         * to remain interactive for text selection or link clicking.
         */
        if (isClickable()) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();
        attachedToWindow = true;
        if (webView != null && !released) {
            webView.onResume();
        }
    }

    @Override
    protected void onDetachedFromWindow() {

        attachedToWindow = false;

        if (webView != null
                && !released) {

            webView.onPause();
        }

        /*
         * Do NOT destroy here.
         *
         * RecyclerView frequently detaches and reattaches child views.
         */
        super.onDetachedFromWindow();
    }

    /**
     * Call when the view is permanently finished.
     *
     * Example:
     * Activity.onDestroy()
     * Fragment.onDestroyView()
     * or permanent ViewHolder disposal.
     */
    public void release() {

        if (released) {
            return;
        }

        released = true;

        renderGeneration++;

        currentText = "";

        plainTextView.setText("");

        if (webView != null) {

            WebView oldWebView = webView;

            webView = null;

            removeAndDestroyWebView(oldWebView);
        }

        onLinkClickListener = null;
    }

    private void removeAndDestroyWebView(
            @NonNull WebView target) {

        try {
            target.stopLoading();
        } catch (Exception ignored) {
        }

        try {
            target.onPause();
        } catch (Exception ignored) {
        }

        try {
            target.removeJavascriptInterface(
                    "AndroidMathBridge"
            );
        } catch (Exception ignored) {
        }

        try {
            target.clearHistory();
        } catch (Exception ignored) {
        }

        try {

            if (target.getParent()
                    instanceof ViewGroup) {

                ((ViewGroup) target.getParent())
                        .removeView(target);
            }

        } catch (Exception ignored) {
        }

        try {
            target.destroy();
        } catch (Exception ignored) {
        }
    }

    // -------------------------------------------------------------------------
    // Configuration changes
    // -------------------------------------------------------------------------

    @Override
    protected void onConfigurationChanged(
            Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        /*
         * Needed if ThemeMode.AUTO and the app changes light/dark mode while
         * this view remains alive.
         */
        updatePlainTextTheme();

        if (themeMode == ThemeMode.AUTO) {
            rerender();
        }
    }

    // -------------------------------------------------------------------------
    // Utilities
    // -------------------------------------------------------------------------

    private static boolean containsMath(
            @Nullable String value) {

        if (value == null
                || value.isEmpty()) {

            return false;
        }

        return MATH_PATTERN
                .matcher(value)
                .find();
    }

    private static String escapeHtml(
            @Nullable String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String escapeHtmlAttribute(
            @Nullable String value) {

        return escapeHtml(value);
    }

    private static String formatFloat(
            float value) {

        return String.format(
                Locale.US,
                "%.2f",
                value
        );
    }

    private static String colorToCss(
            int color) {

        return String.format(
                Locale.US,
                "#%02X%02X%02X",
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }
}
