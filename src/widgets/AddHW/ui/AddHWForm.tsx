'use client'
import  { FC,useState } from "react";
import { toast } from "sonner";
import { Button } from "~/components/ui/button";
import DatePicker from "./DatePicker";
import FileUpload from "../ui/FileUpload";
import { ComboboxDemo } from "../ui/ClassManagers";
import { homeworkSchema, HomeworkFormData } from "../model/schema";
import { HomeworkFormProps } from "../model/types";

export const AddHWForm: FC<HomeworkFormProps> = ({
  onSubmit,
  defaultValues,
  isLoading = false,
}) => {
  const [dueDate, setDueDate] = useState<Date | undefined>(
    defaultValues?.dueDate
  );
  const [files, setFiles] = useState<File[]>(
    defaultValues?.files ?? []
  );
  const [classes, setClasses] = useState<string[]>(
    defaultValues?.classes ?? []
  );
  const [formErrors, setFormErrors] = useState<Record<string, string>>({});
  const [submitAttempted, setSubmitAttempted] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitAttempted(true);
    
    try {
      const formData: HomeworkFormData = {
        dueDate: dueDate!,
        files: files.length > 0 ? files : undefined,
        classes,
      };

      await homeworkSchema.parseAsync(formData);
      setFormErrors({});
      onSubmit?.(formData);
      toast.success("Домашнее задание успешно добавлено!");
    } catch (error: any) {
      const errors: Record<string, string> = {};
      
      if (error.issues) {
        error.issues.forEach((issue: any) => {
          errors[issue.path[0]] = issue.message;
        });
      }
      
      setFormErrors(errors);
      toast.error("Ошибка валидации", {
        description: Object.values(errors).join(", "),
      });
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto">
      <div className="p-6">
        <form onSubmit={handleSubmit} className="space-y-6 max-w-lg mx-auto">
          {/* Поле выбора даты */}
          <div>
            <DatePicker
              date={dueDate}
              onDateChange={setDueDate}
              label="Срок сдачи"
              error={submitAttempted && !dueDate}
            />
            {submitAttempted && !dueDate && (
              <p className="text-red-500 text-xs mt-1">
                {formErrors.dueDate ?? "Пожалуйста, выберите дату сдачи"}
              </p>
            )}
          </div>

          {/* Поле загрузки файлов */}
          <FileUpload
            files={files}
            onFilesChange={setFiles}
            label="Прикрепленные файлы"
          />

          {/* Поле выбора классов */}
          <div>
            {/* Восстановленная надпись */}
            <label className="block text-sm font-medium mb-2">
              Выбрать класс(ы)
            </label>

            <ComboboxDemo
              selectedClasses={classes}
              onClassesChange={setClasses}
              hasError={submitAttempted && classes.length === 0}
            />
            {submitAttempted && classes.length === 0 && (
              <p className="text-red-500 text-xs mt-1">
                {formErrors.classes ?? "Выберите хотя бы один класс"}
              </p>
            )}
          </div>

          {/* Кнопка отправки */}
          <div className="flex justify-center mt-6">
            <Button
              type="submit"
              className="bg-primary hover:bg-primary/90 text-white font-medium py-2 px-8"
              disabled={isLoading} // Теперь кнопка всегда активна (кроме состояния загрузки)
            >
              {isLoading ? "Сохранение..." : "Сохранить"}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};