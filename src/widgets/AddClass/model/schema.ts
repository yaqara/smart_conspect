// model/schema.ts

import { z } from "zod";

export const classSchema = z.object({
  subject: z
    .string()
    .min(3, "Минимум 3 символа")
    .regex(/^[а-яА-ЯёЁ ]+$/i, "Только русские буквы"),
  number: z
    .string() // Остается строкой
    .regex(/^\d+$/, "Только цифры"),
  letter: z
    .string()
    .regex(/^[A-Za-zА-Яа-я]$/i, "Одна буква")
    .length(1, "Только одна буква"),
});

export type ClassFormData = z.infer<typeof classSchema>;