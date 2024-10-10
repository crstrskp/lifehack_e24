/*document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.tier-image');
    const dropZone = document.querySelector('.drop-zone');

    images.forEach(image => {
        image.addEventListener('dragstart', dragStart);
        image.addEventListener('dragend', dragEnd);
    });

    function dragStart(event) {
        event.target.classList.add('dragging');
        event.dataTransfer.setData('text/plain', event.target.id);
    }

    function dragEnd(event) {
        event.target.classList.remove('dragging');
    }

    // Prevent default behavior for dragover to allow drop
    dropZone.addEventListener('dragover', function(event) {
        event.preventDefault(); // Prevent default to allow dropping
    });

    // Handle drop event in the designated drop zone
    dropZone.addEventListener('drop', function(event) {
        event.preventDefault(); // Prevent default behavior (open as link for some elements)
        const draggedImageId = event.dataTransfer.getData('text/plain');
        const draggedImage = document.getElementById(draggedImageId);

        if (draggedImage) {
            const dropX = event.clientX - draggedImage.width / 2;
            const dropY = event.clientY - draggedImage.height / 2;
            draggedImage.style.position = 'absolute';
            draggedImage.style.left = `${dropX}px`;
            draggedImage.style.top = `${dropY}px`;
            dropZone.appendChild(draggedImage);
            draggedImage.classList.remove('dragging');
        }
    });
});*/
document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.tier-image');
    const dropZone = document.querySelector('.drop-zone');

    images.forEach(image => {
        image.addEventListener('dragstart', dragStart);
        image.addEventListener('dragend', dragEnd);
    });

    function dragStart(event) {
        event.target.classList.add('dragging');
        event.dataTransfer.setData('text/plain', event.target.id);
    }

    function dragEnd(event) {
        event.target.classList.remove('dragging');
    }

    // Prevent default behavior for dragover to allow drop
    dropZone.addEventListener('dragover', function(event) {
        event.preventDefault(); // Prevent default to allow dropping
    });

    // Handle drop event in the designated drop zone
    dropZone.addEventListener('drop', function(event) {
        event.preventDefault(); // Prevent default behavior (open as link for some elements)
        const draggedImageId = event.dataTransfer.getData('text/plain');
        const draggedImage = document.getElementById(draggedImageId);

        if (draggedImage) {
            const dropX = event.clientX - draggedImage.width / 2;
            const dropY = event.clientY - draggedImage.height / 2;

            // Set the style for positioning in the drop zone
            draggedImage.style.position = 'absolute'; // Set to absolute
            draggedImage.style.left = `${dropX}px`; // Set left position
            draggedImage.style.top = `${dropY}px`; // Set top position

            // Remove the dragged image from its original position and add it to the drop zone
            dropZone.appendChild(draggedImage);
        }
    });
});