'use client'
import  { FC,useRef } from "react";
import { Button } from "~/components/ui/button";
import { FileUploadProps } from "../model/types";
import {  X } from "lucide-react";
import { Badge } from "~/components/ui/badge";

// Компонент для загрузки файлов
const FileUpload: FC<FileUploadProps> = ({files,onFilesChange,label = "Прикрепленные файлы",}) => {
  // Ссылка на скрытый input элемент для выбора файлов
  const fileInputRef = useRef<HTMLInputElement>(null);

  // Обработчик изменения выбранных файлов
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      // Получаем массив из выбранных файлов
      const newFiles = Array.from(e.target.files);
      
      // Добавляем новые файлы к существующим
      onFilesChange([...files, ...newFiles]);
    }
  };

  // Открыть диалог выбора файлов при нажатии на кнопку
  const handleClick = () => {
    fileInputRef.current?.click();
  };
  
  // Удаление файла из списка
  const handleRemoveFile = (fileToRemove: File) => {
    onFilesChange(files.filter(file => file !== fileToRemove));
  };

  return (
    <div className="flex flex-col gap-2">
      {/* Отображаем метку, если она предоставлена */}
      {label && <label className="text-sm font-medium">{label}</label>}
      
      <div className="w-full space-y-4">
        {/* Скрытый input для файлов */}
        <input
          type="file"
          ref={fileInputRef}
          onChange={handleFileChange}
          className="hidden"
          multiple />
        
        {/* Кнопка для открытия диалога выбора файлов */}
        <Button
          type="button"
          onClick={handleClick}
          variant="outline"
          className="w-full justify-center">
          Перейти в проводник
        </Button>
        
        {/* Отображение выбранных файлов в виде бейджей с крестиком для удаления */}
        {files.length > 0 && (
          <div className="flex flex-wrap gap-2 mt-2">
            {files.map((file, index) => (
              <Badge key={index} variant="secondary" className="px-3 py-1.5 flex items-center">
                <span className="truncate max-w-[150px]">
                  {file.name} ({(file.size / 1024).toFixed(1)} КБ)
                </span>
                <button
                  type="button"
                  onClick={() => handleRemoveFile(file)}
                  className="ml-2 text-gray-500 hover:text-gray-700 focus:outline-none">
                  <X className="h-3 w-3" />
                </button>
              </Badge>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default FileUpload;