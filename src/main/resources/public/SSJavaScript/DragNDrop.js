document.addEventListener('DOMContentLoaded', () => {
    const images = document.querySelectorAll('.tier-image');
    const dropZones = document.querySelectorAll('.drop-zone');


    images.forEach(image => {
        image.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData('text/plain', image.id);
            setTimeout(() => {
                e.target.classList.add('dragging');
                console.log(`Started dragging image with ID: ${image.id}`);
            }, 0);
        });

        image.addEventListener('dragend', (e) => {
            e.target.classList.remove('dragging');
            console.log(`Finished dragging image with ID: ${image.id}`);
        });
    });

    dropZones.forEach(zone => {
        zone.addEventListener('dragover', (e) => {
            e.preventDefault();
            console.log(`Dragging over drop zone: ${zone.id}`);
        });

        zone.addEventListener('drop', (e) => {
            e.preventDefault();
            const imageId = e.dataTransfer.getData('text/plain');
            console.log(`Dropped image with ID: ${imageId}`);
            const draggedImage = document.getElementById(imageId);


            if (!draggedImage) {
                console.error(`Image with ID ${imageId} not found.`);
                return;
            }


            const targetImage = e.target.closest('.tier-image');

            if (targetImage) {

                const targetParent = targetImage.parentNode;


                if (targetParent === draggedImage.parentNode) {

                    targetParent.insertBefore(draggedImage, targetImage);
                    targetParent.insertBefore(targetImage, draggedImage.nextSibling);
                    console.log(`Swapped images: ${draggedImage.id} and ${targetImage.id}`);
                } else {

                    zone.appendChild(draggedImage);
                    console.log(`Moved image: ${draggedImage.id} to drop zone: ${zone.id}`);
                }
            } else {

                zone.appendChild(draggedImage);
                console.log(`Dropped image: ${draggedImage.id} into empty drop zone: ${zone.id}`);
            }
        });
    });
});

