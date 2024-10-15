import { Button } from "flowbite-react";
import {createRoot} from 'react-dom/client'

let element = document.getElementById('flowbite-example-wrapper');
createRoot(element!).render(
    <FlowbiteExample/>
)
export default function FlowbiteExample() {
  return <Button>Click me</Button>;
}