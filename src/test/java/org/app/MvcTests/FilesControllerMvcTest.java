package org.app.MvcTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.app.controller.FilesController;
import org.app.service.FilesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FilesController.class)
public class FilesControllerMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private FilesService filesService;

  @Test
  public void whenGetExistingFile_thenReturnFileInfo() throws Exception {
    String mockFileInfo = "Информация о файле с ID 3 пользователя 1";

    when(filesService.getFileInfo("3", "1")).thenReturn(mockFileInfo);

    mockMvc.perform(get("/files/info/3/1")).andExpect(status().isOk());
  }

  @Test
  public void whenGetNonExistingFile_thenReturnNotFound() throws Exception {
    String mockFileInfo = "Файл не найден";

    when(filesService.getFileInfo("36583", "5")).thenReturn(mockFileInfo);

    mockMvc.perform(get("/files/info/36583/5")).andExpect(status().is(404));
  }

  @Test
  public void whenGetExistingBucket_thenReturnRootDirectories() throws Exception {
    String mockRootDirectories = "Корневые папки бакета Buck1";

    when(filesService.getRootDirectories("Buck1")).thenReturn(mockRootDirectories);

    mockMvc.perform(get("/root/directories/Buck1")).andExpect(status().isOk());
  }
}