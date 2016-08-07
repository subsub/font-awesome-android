package com.eqot.fontawesome;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontAwesome {
    private static final String FONT_FILENAME = "fontawesome-webfont.ttf";

    private static TypefaceSpan sTypefaceSpan = null;
    private static final Pattern pattern = Pattern.compile("([^\\{]*)\\{([\\w\\-]+)\\}(.*)");

    public static void apply(Context context, View view) {
        if (sTypefaceSpan == null) {
            final Typeface typeface = Typeface.createFromAsset(context.getAssets(), FONT_FILENAME);
            sTypefaceSpan = new CustomTypefaceSpan("", typeface);
        }

        final TextView textView = (TextView) view;
        String text = (String) textView.getText();

        final SpannableStringBuilder sb = new SpannableStringBuilder();
        while (true) {
            final Matcher matcher = pattern.matcher(text);
            if (!matcher.find()) {
                sb.append(text);
                break;
            }

            sb.append(matcher.group(1));

            final String orgCode = matcher.group(2);
            final String code = orgCode.replace("-", "_");
            final int id = context.getResources().getIdentifier(code, "string", context.getPackageName());
            final String string = context.getResources().getString(id);
            sb.append(string, sTypefaceSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            text = matcher.group(3);
        }

        textView.setText(sb);
    }
}
