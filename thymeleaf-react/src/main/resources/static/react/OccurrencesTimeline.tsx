import {
  Timeline,
  TimelineBody,
  TimelineContent,
  TimelineItem,
  TimelinePoint,
  TimelineTime,
  TimelineTitle
} from "flowbite-react";
import {createRoot} from "react-dom/client";

let element = document.getElementById('timeline-wrapper')!;
createRoot(element).render(
    <OccurrencesTimeline/>
)

type Occurrence = {
  date: string;
  title: string;
  content: string;
};

export default function OccurrencesTimeline() {
  const occurrences: Occurrence[] = JSON.parse(document.getElementById('occurrences-data')!.innerHTML)
  console.log(occurrences)

  return (
      <Timeline>
        {occurrences.map(({date, title, content}) =>
            <TimelineItem>
              <TimelinePoint/>
              <TimelineContent>
                <TimelineTime>{date}</TimelineTime>
                <TimelineTitle>{title}</TimelineTitle>
                <TimelineBody>
                  {content}
                </TimelineBody>
              </TimelineContent>
            </TimelineItem>)}

      </Timeline>
  )
}