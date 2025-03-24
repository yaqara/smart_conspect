import { z } from "zod";

// Определяем схему валидации для формы домашнего задания
export const homeworkSchema = z.object({
  // Дата выполнения задания
  dueDate: z.date({
    required_error: "Пожалуйста, выберите дату сдачи",
  }),
  
  // Прикрепленные файлы (не обязательно)
  files: z.array(z.instanceof(File)).optional(),
  
  // Список классов, для которых предназначено задание
  classes: z.array(z.string()).min(1, {
    message: "Пожалуйста, добавьте хотя бы один класс",
  }),
  

});

// Тип данных, соответствующий схеме
export type HomeworkFormData = z.infer<typeof homeworkSchema>;