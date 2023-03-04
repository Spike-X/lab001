package com.aircraft.codelab.pioneer.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 2021-11-27
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface ContractStateEnum {
    int SIGNING = 0;
    int END = 1;
    int CANCEL = 2;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    enum CONTRACT_SIGNING {
        PENDING(0, "待办"),
        PRIVACY(1, "隐私"),
        IDENTITY(2, "身份证"),
        FACE_RECOGNITION(3, "人脸识别"),
        SPEECH_SYNTHESIS(4, "语音合成"),
        SPEECH_RECOGNITION(5, "语音识别"),
        ;

        @Getter
        private final int code;
        @Getter
        private final String name;

        CONTRACT_SIGNING(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    enum CONTRACT_END {
        SIGNATURE(6, "签字(结束)"),
        ;

        @Getter
        private final int code;
        @Getter
        private final String name;

        CONTRACT_END(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    enum CONTRACT_CANCEL {
        CANCEL(7, "取消");

        @Getter
        private final int code;
        @Getter
        private final String name;

        CONTRACT_CANCEL(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
