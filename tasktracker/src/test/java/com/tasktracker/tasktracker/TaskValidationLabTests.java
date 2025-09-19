package com.tasktracker.tasktracker;

import com.tasktracker.tasktracker.model.Task;
import com.tasktracker.tasktracker.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TaskValidationLabTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    // Guided lab test: this test asserts that posting a Task with an empty title
    // should
    // return the form view with validation errors. It is intentionally expected to
    // fail
    // for learners who haven't yet wired up @Valid + BindingResult handling or
    // configured form error display.
    @Test
    public void postEmptyTitleShouldReturnFormWithErrors() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "")
                .param("description", "some desc")
                .param("dueDate", "2025-09-18"))
                // Expect to get back the form view (not a redirect)
                .andExpect(status().isOk())
                .andExpect(view().name("task_form"))
                // Expect the model to contain errors (binding errors)
                .andExpect(model().attributeHasFieldErrors("task", "title"));
    }
}