import {createRoot} from 'react-dom/client'

let element = document.getElementById('list-example-wrapper');
createRoot(element!).render(
    <ListExample/>
)

export default function ListExample() {
  return (
      <ul>
        {["Wim", "Thomas", "Oliver"]
        .map(name => <li key={name}>Hello {name} from React</li>)}
      </ul>
  )
}