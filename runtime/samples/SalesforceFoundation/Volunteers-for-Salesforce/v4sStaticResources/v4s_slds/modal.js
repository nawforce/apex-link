(function () {
    var addClass = function(el, class_) {
        var currentClass = el.getAttribute('class') || '';
        el.setAttribute(
            'class',
            class_ + ' ' + currentClass
        );
    };

    var removeClass = function(el, class_) {
        var currentClass = el.getAttribute('class') || '';
        var regex = new RegExp(
            '(^|\\b)' + class_.split(' ').join('|') + '(\\b|$)',
            'gi'
        );
        el.setAttribute(
            'class',
            currentClass.replace(regex, ' ')
        );
    };

    var elementMatches = function(el, selector) {
        var m = el.matches || el.matchesSelector || el.msMatchesSelector;
        return m.call(el, selector);
    };

    var currentModalId;
    var lastFocusedElement;

    var openModal = function(modalId) {
        var modalElement = document.getElementById(modalId);

        // if the target modal element doesn't exist, bail out
        if (!modalElement) return;

        // close any currently open modal dialogs before opening this one
        if (currentModalId) {
            if (currentModalId === modalId) return;
            closeModal(currentModalId);
        }

        // set this modal as the currently open modal
        currentModalId = modalId;

        // find the modal's backdrop and make it visible
        var backdropElement = modalElement.parentElement.querySelector('.slds-backdrop');
        if (backdropElement) {
            addClass(backdropElement, 'slds-backdrop--open');
        }

        // make the modal visible
        addClass(modalElement, 'slds-fade-in-open');

        // switch focus to the modal
        modalElement.focus();

        // set the modal visible to accessability tools
        modalElement.setAttribute('aria-hidden', false);

        // hide all elements in the "background" of the modal
        Array.prototype.forEach.call(
            document.querySelectorAll('.hide-when-modal-open'),
            function (el) {
                el.setAttribute('aria-hidden', 'true');
            }
        );
    }

    var closeModal = function(modalId) {
        var modalElement = document.getElementById(modalId);

        // if the target modal element doesn't exist, bail out
        if (!modalElement) return;

        // show all elements that were previously hidden in the "background" of the modal
        Array.prototype.forEach.call(
            document.querySelectorAll('.hide-when-modal-open'),
            function (el) {
                el.setAttribute('aria-hidden', 'false');
            }
        );

        // restore focus to the last used element before the modal was opened
        if (lastFocusedElement) {
            lastFocusedElement.focus();
        }

        // hide the modal dialog
        removeClass(modalElement, 'slds-fade-in-open');

        // set the modal invisible to accessability tools
        modalElement.setAttribute('aria-hidden', true);

        // find the modal's backdrop and hide it
        var backdropElement = modalElement.parentElement.querySelector('.slds-backdrop');
        if (backdropElement) {
            removeClass(backdropElement, 'slds-backdrop--open');
        }

        // we've closed this modal so there should not be a current modal
        currentModalId = '';
    }

    document.addEventListener('click', function (event) {
        if (event.defaultPrevented) return;

        var el = event.target;

        var isModalAction = false;

        if (elementMatches(el, '[data-toggle="modal"], [data-toggle="modal"] *')) {
            var modalId = el.getAttribute('data-target') || '';
            if (!modalId) return;
            isModalAction = true;
            lastFocusedElement = el;
            openModal(modalId);
        }

        if (elementMatches(el, '[data-dismiss="modal"], [data-dismiss="modal"] *')) {
            var modalId = el.getAttribute('data-target') || '';
            if (!modalId) return;
            isModalAction = true;
            closeModal(modalId);
        }

        if (isModalAction && elementMatches(el, 'a')) {
            event.preventDefault();
        }
    }, false);

    document.addEventListener('keyup', function (event) {
        if (!currentModalId) return;

        if (event.keyCode === 27) {
            closeModal(currentModalId);
        }
    }, true);

    document.addEventListener('blur', function (event) {
        if (!currentModalId) return;

        var modalElement = document.getElementById(currentModalId);

        if (modalElement && !modalElement.contains(event.relatedTarget)) {
            event.preventDefault();
            event.stopPropagation();
            modalElement.focus();
        }
    }, true);
})();
