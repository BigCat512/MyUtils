package org.example.common.utils.corn;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CronExpressionParser {

    private final CronDescriptor englishDescriptor;
    private final CronDescriptor chineseDescriptor;

    public CronExpressionParser() {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        CronParser cronParser = new CronParser(cronDefinition);
        Cron cron = cronParser.parse("0 0/30 * * * ?");
        englishDescriptor = CronDescriptor.instance(Locale.US);
        chineseDescriptor = CronDescriptor.instance(Locale.CHINA);
    }

    public static void main(String[] args) {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        CronParser cronParser = new CronParser(cronDefinition);
        Cron cron = cronParser.parse("0 0 5 2,3,20 * ?");
        CronDescriptor englishDescriptor = CronDescriptor.instance(Locale.US);
        CronDescriptor chineseDescriptor = CronDescriptor.instance(Locale.CHINA);
        CronDescriptor descriptor;
        if ("zh".equalsIgnoreCase(Locale.CHINA.getLanguage())) {
            descriptor = chineseDescriptor;
        } else {
            descriptor = englishDescriptor;
        }
        System.out.println(descriptor.describe(cron));
    }

    public String parseCronExpression(String cronExpression, String language) {
        CronDescriptor descriptor;
        if ("zh".equalsIgnoreCase(language)) {
            descriptor = chineseDescriptor;
        } else {
            descriptor = englishDescriptor;
        }
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        CronParser cronParser = new CronParser(cronDefinition);
        Cron cron = cronParser.parse(cronExpression);
        return descriptor.describe(cron);
    }
}
