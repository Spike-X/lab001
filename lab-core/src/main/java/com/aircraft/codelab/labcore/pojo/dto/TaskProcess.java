package com.aircraft.codelab.labcore.pojo.dto;

import com.aircraft.codelab.labcore.enums.TaskStateEnum;
import lombok.Data;

/**
 * 2021-12-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class TaskProcess {
    private TaskStateEnum state = TaskStateEnum.START;

    public void nextState() {
        this.state = this.state.nextState();
    }

    public void log() {
        System.out.println(this.state.prevState() + "--->" + this.state.name());
    }

    public static void main(String[] args) {
        TaskProcess taskProcess = new TaskProcess();
//        TaskState instance = TaskState.getInstance(1001);
//        TaskState taskState = instance.nextState();
//        taskProcess.setState(instance);

        taskProcess.nextState();
        taskProcess.log();
        taskProcess.nextState();
        taskProcess.log();

        System.out.println(taskProcess.state.prevState().name());
    }
}
