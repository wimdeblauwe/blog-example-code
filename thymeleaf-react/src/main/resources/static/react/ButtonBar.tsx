import { Button } from "flowbite-react";
import {createRoot} from 'react-dom/client'

let element = document.getElementById('button-bar-wrapper')!;
const okButtonLabel = element.getAttribute('ok-label') || "OK";
const cancelButtonLabel = element.getAttribute('cancel-label') || "Cancel";
createRoot(element).render(
    <ButtonBar okButtonLabel={okButtonLabel} cancelButtonLabel={cancelButtonLabel}/>
)

interface ButtonBarProps {
  okButtonLabel: string;
  cancelButtonLabel: string;
}

export default function ButtonBar({okButtonLabel, cancelButtonLabel}:ButtonBarProps) {
  return (
      <div className="flex justify-around bg-gray-100 rounded-md py-2">
        <Button>{okButtonLabel}</Button>
        <Button color="light">{cancelButtonLabel}</Button>
      </div>
  );
}

