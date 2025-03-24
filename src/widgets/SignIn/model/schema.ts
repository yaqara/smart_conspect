
import { z } from "zod";

// Схема валидации для формы входа
export const loginSchema = z.object({
  email: z
    .string()
    .min(1, { message: "Введите почту" })
    .email({ message: "Неверный формат почты" }),
  password: z
    .string()
    .min(1, { message: "Введите пароль" })
    .min(6, { message: "Пароль должен содержать минимум 6 символов" }),
  rememberMe: z.boolean().optional(),
});

// Тип данных на основе схемы
export type LoginFormData = z.infer<typeof loginSchema>;

// Функция для проверки учетных данных (в реальном проекте была бы API-вызовом)
export const validateCredentials = (
  email: string,
  password: string
): Promise<boolean> => {
  return new Promise((resolve) => {
    // Эмуляция проверки (в реальном приложении здесь был бы API-вызов)
    // Для упрощения считаем валидными пользователями:
    const validUsers = [
      { email: "123@mail.com", password: "111111" },

    ];

    const isValid = validUsers.some(
      (user) => user.email === email && user.password === password
    );

    // Имитация задержки сети
    setTimeout(() => {
      resolve(isValid);
    }, 500);
  });
};
