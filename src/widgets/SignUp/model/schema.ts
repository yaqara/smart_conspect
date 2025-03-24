// src/widgets/SignUp/model/schema.ts
import { z } from "zod";

export const registrationSchema = z
  .object({
    firstName: z
      .string()
      .min(1, { message: "Введите имя" })
      .max(50, { message: "Имя должно быть не более 50 символов" }),
    lastName: z
      .string()
      .min(1, { message: "Введите фамилию" })
      .max(50, { message: "Фамилия должна быть не более 50 символов" }),
    patronymic: z
      .string()
      .min(1, { message: "Введите отчество" })
      .max(50, { message: "Отчество должно быть не более 50 символов" }),
    email: z
      .string()
      .min(1, { message: "Введите почту" })
      .email({ message: "Неверный формат почты" }),
    password: z
      .string()
      .min(6, { message: "Пароль должен содержать минимум 6 символов" }),
    confirmPassword: z.string().min(1, { message: "Подтвердите пароль" }),
  })
  .refine(
    (data) => data.password === data.confirmPassword,
    {
      message: "Пароли не совпадают",
      path: ["confirmPassword"], // Указываем, к какому полю относится ошибка
    }
  );

export type RegistrationFormData = z.infer<typeof registrationSchema>;

// Функция для эмуляции регистрации
export const registerUser = (
  formData: RegistrationFormData
): Promise<boolean> => {
  return new Promise((resolve) => {
    setTimeout(() => resolve(true), 500);
  });
};