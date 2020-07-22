package com.tzwjkl.bowl.autoindex.web.pebble;

import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Component
public class SizeFormatFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return List.of("scale");
    }

    @Override
    public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        if (input instanceof Number) {
            int scale;
            Object o = args.get("scale");
            if (o instanceof String) {
                String str = (String) o;
                scale = Integer.parseInt(str);
            } else if (o instanceof Number) {
                Number num = (Number) o;
                scale = num.intValue();
            } else {
                scale = 2;
            }
            Number size = (Number) input;
            double d = size.doubleValue();
            if (d < 1024) {
                return BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_DOWN) + " Bytes";
            } else if (d < 1048576) {
                return BigDecimal.valueOf(d / 1024).setScale(scale, RoundingMode.HALF_DOWN) + " KB";
            } else if (d < 1073741824) {
                return BigDecimal.valueOf(d / 1048576).setScale(scale, RoundingMode.HALF_DOWN) + " MB";
            } else {
                return BigDecimal.valueOf(d / 1073741824).setScale(scale, RoundingMode.HALF_DOWN) + " GB";
            }
        }
        return null;
    }
}