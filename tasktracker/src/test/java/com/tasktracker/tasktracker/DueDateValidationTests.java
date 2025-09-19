package com.tasktracker.tasktracker;

import com.tasktracker.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class DueDateValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void pastDueDateShouldReturnFormWithError() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "Test")
                .param("description", "desc")
                .param("dueDate", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("task_form"))
                .andExpect(model().attributeHasFieldErrors("task", "dueDate"));
    }
}
