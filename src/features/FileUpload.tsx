
import React, { useRef, useState } from 'react';
import { PaperclipIcon } from 'lucide-react';
import { Button } from '~/components/ui/button';

interface FileUploadProps {
  onChange: (files: File[]) => void;
  value?: File[];
}

const FileUpload: React.FC<FileUploadProps> = ({ onChange, value = [] }) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [selectedFiles, setSelectedFiles] = useState<File[]>(value);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const filesArray = Array.from(e.target.files);
      setSelectedFiles(filesArray);
      onChange(filesArray);
    }
  };

  const handleClick = () => {
    fileInputRef.current?.click();
  };

  return (
    <div className="relative w-full">
      <input
        type="file"
        ref={fileInputRef}
        onChange={handleFileChange}
        className="hidden"
        multiple
      />
      <Button
        type="button"
        onClick={handleClick}
        variant="outline"
        className="w-full justify-start file-upload-button"
      >
        <PaperclipIcon className="w-5 h-5 mr-2" />
        {selectedFiles.length > 0 
          ? `${selectedFiles.length} файл(ов) выбрано` 
          : 'Перейти в проводник'}
      </Button>
      {selectedFiles.length > 0 && (
        <div className="mt-2 text-sm text-gray-500 space-y-1">
          {selectedFiles.map((file, index) => (
            <div key={index} className="animate-fade-in">
              {file.name} ({(file.size / 1024).toFixed(1)} KB)
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default FileUpload;