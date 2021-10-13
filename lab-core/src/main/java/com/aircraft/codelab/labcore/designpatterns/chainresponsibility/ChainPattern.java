package com.aircraft.codelab.labcore.service.impl;

import lombok.Builder;
import lombok.Data;

/**
 * 2021-07-21
 *
 * @author tao.zhang
 * @since 1.0
 */
public class ChainPattern {
    @Data
    @Builder
    public static class LeaveRequest {
        /**
         * 天数
         */
        private int leaveDays;

        /**
         * 姓名
         */
        private String name;
    }

    public static abstract class AbstractLeaveHandler {
        /**
         * 直接主管审批处理的请假天数
         */
        protected int MIN = 2;
        /**
         * 部门经理处理的请假天数
         */
        protected int MIDDLE = 5;
        /**
         * 总经理处理的请假天数
         */
        protected int MAX = 14;

        /**
         * 领导名称
         */
        protected String handlerName;

        /**
         * 下一个处理节点（即更高级别的领导）
         */
        protected AbstractLeaveHandler nextHandler;

        /**
         * 设置下一节点
         */
        protected void setNextHandler(AbstractLeaveHandler handler) {
            this.nextHandler = handler;
        }

        /**
         * 处理请假的请求，子类实现
         */
        abstract protected void handlerRequest(LeaveRequest request);
    }

    public static class DirectLeaderLeaveHandler extends AbstractLeaveHandler {
        public DirectLeaderLeaveHandler(String name) {
            this.handlerName = name;
        }

        @Override
        protected void handlerRequest(LeaveRequest request) {
            System.out.println("直接主管:" + handlerName + ",已经处理;");
            if (request.getLeaveDays() > MIN) {
                if (null != this.nextHandler) {
                    this.nextHandler.handlerRequest(request);
                } else {
                    System.out.println(handlerName + " :审批拒绝！");
                }
            }
        }
    }

    public static class DeptManagerLeaveHandler extends AbstractLeaveHandler {

        public DeptManagerLeaveHandler(String name) {
            this.handlerName = name;
        }

        @Override
        protected void handlerRequest(LeaveRequest request) {
            System.out.println("部门经理:" + handlerName + ",已经处理;");
            if (request.getLeaveDays() > MIDDLE) {
                if (null != this.nextHandler) {
                    this.nextHandler.handlerRequest(request);
                } else {
                    System.out.println(handlerName + " :审批拒绝！");
                }
            }
        }
    }

    public static class GManagerLeaveHandler extends AbstractLeaveHandler {
        public GManagerLeaveHandler(String name) {
            this.handlerName = name;
        }

        @Override
        protected void handlerRequest(LeaveRequest request) {
            if (request.getLeaveDays() <= this.MAX) {
                System.out.println("总经理:" + handlerName + ",已经处理;");
                return;
            }

            if (null != this.nextHandler) {
                this.nextHandler.handlerRequest(request);
            } else {
                System.out.println(handlerName + " :审批拒绝！");
            }
        }
    }

    public static void main(String[] args) {
        LeaveRequest request = LeaveRequest.builder().leaveDays(14).name("小明").build();

        DirectLeaderLeaveHandler directLeaderLeaveHandler = new DirectLeaderLeaveHandler("A");
        DeptManagerLeaveHandler deptManagerLeaveHandler = new DeptManagerLeaveHandler("B");
        GManagerLeaveHandler gManagerLeaveHandler = new GManagerLeaveHandler("C");

        directLeaderLeaveHandler.setNextHandler(deptManagerLeaveHandler);
        deptManagerLeaveHandler.setNextHandler(gManagerLeaveHandler);

        directLeaderLeaveHandler.handlerRequest(request);
    }
}
