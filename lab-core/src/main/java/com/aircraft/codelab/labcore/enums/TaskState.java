package com.aircraft.codelab.labcore.enums;

import com.aircraft.codelab.core.exception.ApiException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 2021-12-26
 *
 * @author tao.zhang
 * @since 1.0
 */
public enum TaskState {
    START(1000, "任务开始") {
        @Override
        public TaskState nextState() {
            return PROGRESSING;
        }

        @Override
        public TaskState prevState() {
            return this;
        }
    },

    PROGRESSING(1001, "任务执行中") {
        @Override
        public TaskState nextState() {
            return END;
        }

        @Override
        public TaskState prevState() {
            return START;
        }
    },

    END(1002, "任务结束") {
        @Override
        public TaskState nextState() {
            return END;
        }

        @Override
        public TaskState prevState() {
            return PROGRESSING;
        }
    };

    @Getter
    private final int code;
    @Getter
    private final String taskName;

    TaskState(int code, String taskName) {
        this.code = code;
        this.taskName = taskName;
    }

    public abstract TaskState nextState();

    public abstract TaskState prevState();

    public static TaskState getInstance(int code) {
        return Arrays.stream(TaskState.values()).filter(s -> s.code == code).findFirst().orElseThrow(() -> new ApiException("Not found the enum code"));
    }
}
