import { HomeworkFormData } from "../model/schema";

// Пропсы для компонента формы домашнего задания
export interface HomeworkFormProps {
  // Функция, которая будет вызвана при успешной отправке формы
  onSubmit?: (data: HomeworkFormData) => void;
  
  // Начальные значения формы (опционально)
  defaultValues?: Partial<HomeworkFormData>;
  
  // Флаг загрузки (для отображения состояния загрузки)
  isLoading?: boolean;
}

// Пропсы для компонента выбора даты
// В файле типов (types.ts)
export interface DatePickerProps {
  date: Date | undefined;
  onDateChange: (date: Date | undefined) => void;
  label: string;
  error?: boolean; // Добавлено
}

// Пропсы для компонента загрузки файлов
export interface FileUploadProps {
  files: File[];
  onFilesChange: (files: File[]) => void;
  label: string;
}

// Пропсы для компонента управления классами
export interface ClassManagerProps {
  classes: string[];
  onClassesChange: (classes: string[]) => void;
  label: string;
}
