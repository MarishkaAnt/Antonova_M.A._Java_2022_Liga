package org.liga.enums;

import org.liga.util.StringConstants;

import java.util.List;

public enum CommonCommands {

    HELP {
        @Override
        public String action(List<String> parameters) {
            return StringConstants.HELP_TEXT;
        }
    },
    EXIT {
        @Override
        public String action(List<String> parameters) {
            return StringConstants.GOODBYE_MESSAGE;
        }
    };

    public abstract String action(List<String> parameters);

}
