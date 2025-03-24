import {FC} from 'react';
import {Textarea} from '~/components/ui/textarea';
interface CommentProps {
  comment:string;
  onChange:(comment:string) => void;
}

export const Comment:FC<CommentProps > =({comment, onChange }) => {
  return (
    <div className="mb-6">
      <Textarea
        placeholder="Введите краткий комментарий"
        value={comment}
        onChange={(e) => onChange(e.target.value)}
        className="min-h-[120px] border-gray-300 focus:border-primary focus:ring-1 focus:ring-primary"
      />
    </div>
  );
};