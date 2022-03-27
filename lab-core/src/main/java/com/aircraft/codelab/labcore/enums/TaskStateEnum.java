package com.aircraft.codelab.labcore.enums;

import com.aircraft.codelab.core.exception.ApiException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2021-12-26
 *
 * @author tao.zhang
 * @since 1.0
 */
public enum TaskStateEnum {
    /**
     *
     */
    START(1000, "任务开始") {
        @Override
        public TaskStateEnum nextState() {
            return PROGRESSING;
        }

        @Override
        public TaskStateEnum prevState() {
            return this;
        }
    },

    PROGRESSING(1001, "任务执行中") {
        @Override
        public TaskStateEnum nextState() {
            return END;
        }

        @Override
        public TaskStateEnum prevState() {
            return START;
        }
    },

    END(1002, "任务结束") {
        @Override
        public TaskStateEnum nextState() {
            return END;
        }

        @Override
        public TaskStateEnum prevState() {
            return PROGRESSING;
        }
    };

    @Getter
    private final int code;
    @Getter
    private final String taskName;

    TaskStateEnum(int code, String taskName) {
        this.code = code;
        this.taskName = taskName;
    }

    public abstract TaskStateEnum nextState();

    public abstract TaskStateEnum prevState();

    public static TaskStateEnum getInstance(int code) {
        return Arrays.stream(TaskStateEnum.values()).filter(s -> s.code == code).findFirst().orElseThrow(() -> new ApiException("Not found the enum code"));
    }

    public static TaskStateEnum getInstance(String taskName) {
        return Arrays.stream(TaskStateEnum.values()).filter(s -> s.taskName.equals(taskName)).findFirst().orElseThrow(() -> new ApiException("Not found the enum name"));
    }
}
