package xyz.bumbing.api.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class P6spySqlFormatConfig {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomMessageFormat.class.getName());
    }


    public static class CustomMessageFormat implements MessageFormattingStrategy {
        @Override
        public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
            sql = formatSql(category, sql);
            Date _now = new Date();

            SimpleDateFormat format1 = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
            return format1.format(_now) + "|" + elapsed + "ms" + sql;
        }

        private String formatSql(String category, String sql) {
            if (sql == null || sql.trim().equals("")) return sql;

            // Only format Statement, distinguish DDL And DML
            if (Category.STATEMENT.getName().equals(category)) {
                String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
                if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                    sql = FormatStyle.DDL.getFormatter().format(sql);
                } else {
                    sql = FormatStyle.BASIC.getFormatter().format(sql);
                }
                sql = "|\nHeFormatSql(P6Spy sql,Hibernate format):" + sql;
            }

            return sql;
        }
    }
}