import htmx from 'htmx.org';
import flatpickr from 'flatpickr';
import 'css/app.css';


/**
 * Register an event at the document for the specified selector,
 * so events are still catched after DOM changes.
 */
function handleEvent(eventType, selector, handler) {
  document.addEventListener(eventType, function(event) {
    if (event.target.matches(selector + ', ' + selector + ' *')) {
      handler.apply(event.target.closest(selector), arguments);
    }
  });
}

handleEvent('change', '.js-selectlinks', function(event) {
  htmx.ajax('GET', this.value);
  history.pushState({ htmx: true}, '', this.value);
});

handleEvent('click', '.js-dropdown', function(event) {
  document.querySelectorAll('.js-dropdown').forEach(($dropdown) => {
    if (this === $dropdown ||
        ($dropdown.getAttribute('data-dropdown-single') !== 'true' && $dropdown.ariaExpanded === 'true')) {
      $dropdown.ariaExpanded = $dropdown.ariaExpanded !== 'true';
      $dropdown.nextElementSibling.classList.toggle('hidden');
    }
  });
  return false;
});

function initDatepicker() {
  document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig = {
      allowInput: true,
      time_24hr: true,
      enableSeconds: true
    };
    if ($item.classList.contains('js-datepicker')) {
      flatpickrConfig.dateFormat = 'Y-m-d';
    } else if ($item.classList.contains('js-timepicker')) {
      flatpickrConfig.enableTime = true;
      flatpickrConfig.noCalendar = true;
      flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
      flatpickrConfig.enableTime = true;
      flatpickrConfig.altInput = true;
      flatpickrConfig.altFormat = 'Y-m-d H:i:S';
      flatpickrConfig.dateFormat = 'Y-m-dTH:i:S';
    }
    flatpickr($item, flatpickrConfig);
  });
}
document.addEventListener('htmx:afterSwap', initDatepicker);
initDatepicker();
