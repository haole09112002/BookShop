// JavaScript để xử lý sự kiện thay đổi màu
function changeColor(tag) {
    var selectedTags = document.getElementsByClassName("selected");
    for (var i = 0; i < selectedTags.length; i++) {
        selectedTags[i].classList.remove("selected");
    }
    tag.classList.add("selected");
}
function showSubTags() {
    var tagList = document.getElementById("tag-list");
    var tag1 = document.querySelector("#tag-list li:first-child");

    if (tagList.contains(document.getElementById("sub-tags"))) {
        // Nếu #sub-tags đã tồn tại, xóa nó đi
        tagList.removeChild(document.getElementById("sub-tags"));
    } else {
        // Tạo #sub-tags và thêm các tag con vào nó
        var subTags = document.createElement("ul");
        subTags.id = "sub-tags";
        subTags.innerHTML = "<li>Tag 1.1</li><li>Tag 1.2</li>";

        // Chèn #sub-tags giữa Tag 1 và Tag 2
        tagList.insertBefore(subTags, tag1.nextSibling);
    }
}
window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {

        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});


function getBookById(id) {
    resetModal();
    fetch('/bookshop/books?id=' + id).then(function (response) {
        return response.json();
    }).then(function (book) {
        console.log(book)
        jQuery('#id').val(book.id);
        jQuery('#title').val(book.title);
        jQuery('#price').val(book.price);
        jQuery('#quantity').val(book.quantity);
        jQuery('#publisher').val(book.publisher);
        jQuery('#publishYear').val(book.publishYear);
        jQuery('#language').val(book.language);
        jQuery('#supplier').val(book.supplier);
        jQuery('#weight').val(book.weight);
        jQuery('#pageCount').val(book.pageCount);
        jQuery('#formality').val(book.formality);
        jQuery('#ageRange').val(book.ageRange);
        jQuery('#size').val(book.size);
        jQuery('#category').val(book.category.id);
        jQuery('#decription').val(book.decription)
        jQuery('#author').val(book.author.id);
        jQuery('#exampleModalLabel').text(book.title);

        htmlPreviewPic = "";
        htmlPreviewThumbnail = "";

        if (book.bookImages != null) {
            book.bookImages.forEach(img => {
                htmlPreviewPic +=

                    `<div class="` + (img.id == book.bookImages[0].id ? "tab-pane active" : "tab-pane") + `" a id="pic-` + img.id + `">
                        <img src="`+ img.url + `" style="max-width: 100%; max-height: 100%;" />
                        </div>
                    </div>`
                htmlPreviewThumbnail +=
                    `<li class=" ` + (img.id == book.bookImages[0].id ? 'active' : '') + `">
                            <a data-target="#pic-`+ img.id + `" data-toggle="tab">  
                            <img class="img-info" src="`+ img.url + `" />
                            </a>
                        </li>`

            });
            // console.log(html);
        }
        jQuery('#preview-pic').html(htmlPreviewPic);
        jQuery('#preview-thumbnail').html(htmlPreviewThumbnail);


        // $('#exampleModal').modal('show');
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });
}
document.addEventListener('DOMContentLoaded', function () {
    // Lấy tất cả các tab xem trước nhỏ
    var previewTabs = document.querySelectorAll('.preview-thumbnail a');

    // Lặp qua từng tab và thêm bộ lắng nghe sự kiện click
    previewTabs.forEach(function (tab) {
        tab.addEventListener('click', function (event) {
            event.preventDefault();

            // Lấy đường dẫn ảnh từ thuộc tính data-target của tab
            var targetImage = this.getAttribute('data-target');

            // Lấy phần tử hiển thị ảnh chính
            var mainImage = document.querySelector(targetImage);

            // Xóa lớp active từ tất cả các tab và ảnh chính
            previewTabs.forEach(function (tab) {
                tab.parentElement.classList.remove('active');
            });
            document.querySelectorAll('.preview-pic .tab-pane').forEach(function (previewImage) {
                previewImage.classList.remove('active');
            });

            // Thêm lớp active vào tab được nhấp và ảnh chính tương ứng
            this.parentElement.classList.add('active');
            mainImage.classList.add('active');
        });
    });
});

function addBook() {
    resetModal();
    jQuery('#exampleModalLabel').text("Thêm sách mới");
    // jQuery('#preview-pic').html("");
    // jQuery('#preview-thumbnail').html("");
    // jQuery('#decription').val("");
    // jQuery('#my-form').removeClass("was-validated");
    jQuery('#id').prop('disabled', true);
    // $('#exampleModal').modal('show');
}
function saveChange() {
    form = document.getElementById("my-form");
    if (form.checkValidity()) {
        id = jQuery('#id').val();
        title = jQuery('#title').val();
        price = jQuery('#price').val();
        quantity = jQuery('#quantity').val();
        publisher = jQuery('#publisher').val();
        publishYear = jQuery('#publishYear').val();
        language = jQuery('#language').val();
        supplier = jQuery('#supplier').val();
        weight = jQuery('#weight').val();
        pageCount = jQuery('#pageCount').val();
        formality = jQuery('#formality').val();
        ageRange = jQuery('#ageRange').val();
        size = jQuery('#size').val();
        category = jQuery('#category').val();
        author = jQuery('#author').val();
        decription = jQuery('#decription').val()


        const fileInput = document.getElementById('formFileMultiple');
        const images = fileInput.files;
        // Tạo đối tượng FormData
        const formData = new FormData();
        if (id != null && id != '') {
            formData.append('id', id);
            var removeImageId = $('.img-checkbox').not(':checked').map(function () {
                return $(this).val();
            }).get();
            console.log(removeImageId)
            formData.append("removeImage", removeImageId);
        }

        formData.append('title', title);
        formData.append('price', price);
        formData.append('quantity', quantity);
        formData.append('publisher', publisher);
        formData.append('publishYear', publishYear);
        formData.append('language', language);
        formData.append('supplier', supplier);
        formData.append('weight', weight);
        formData.append('pageCount', pageCount);
        formData.append('formality', formality);
        formData.append('ageRange', ageRange);
        formData.append('size', size);
        formData.append('categoryId', category);
        formData.append('authorId', author);
        formData.append('description', decription);


        for (let i = 0; i < images.length; i++) {
            formData.append('images', images[i]);
        }
        fetch('/bookshop/admin/books/add', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    if (id != null && id != '') { //update
                        alert("Cập nhật thành công!");
                        window.location = "/bookshop/admin/books/new/" + id;
                    } else   // add
                    {
                        form.reset();
                        alert("Thêm thành công!");
                    }
                }
                else {
                    throw new Error(response.status);
                }

            })
            .catch(error => {
                console.log(error);
            });

        // bookRequest = {
        //     title,
        //     price,
        //     publisher,
        //     language,
        //     supplier,
        //     weight,
        //     pageCount,
        //     formality,
        //     ageRange,
        //     size,
        //     category,
        //     author,
        //     decription,
        //     images
        // }
    }
    else {
        form.classList.add('was-validated');
    }

}
// jQuery("#close").on('click', function () {
//     jQuery("#exampleModal").modal("hide");
// })

jQuery("#btnNewCategory").on('click', function () {
    jQuery("#modalNewCategory").modal("show");
})

function resetModal() {
    // Reset modal state khi mở lại
    jQuery('#exampleModal').on('hidden.bs.modal', function () {
        jQuery(this).find('form').trigger('reset');
        jQuery('#preview-pic').html("");
        jQuery('#preview-thumbnail').html("");
    });
}


// Gửi dữ liệu thông qua Fetch API
function saveCategory() {
    form = document.getElementById("formNewCategory");
    if (form.checkValidity()) {
        var name = jQuery('#categoryName').val();
        var description = jQuery('#categoryDecription').val();
        categoryRequest = { name: name, description: description };
        console.log(categoryRequest)
        fetch('/bookshop/admin/categories/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(categoryRequest)
        }
        ).then(response => {
            if (response.ok) {
                jQuery("#newCategoryModal").modal("hide");

                response.json().then(category => {
                    console.log(category);
                    var newOption = jQuery('<option></option>').attr('value', category.id).text(category.name);
                    jQuery('#category').append(newOption);
                });

            } else {
                throw new Error(response.status);
            }
        }).catch(err => {
            console.log(err);
        })

    } else {
        form.classList.add('was-validated');
    }
}

function saveAuthor() {
    form = document.getElementById("formNewAuthor");
    if (form.checkValidity()) {
        var name = jQuery('#authorName').val();
        var description = jQuery('#authorDescription').val();
        authorRequest = { fullname: name, description: description };
        console.log(authorRequest)
        fetch('/bookshop/admin/authors/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(authorRequest)
        }
        ).then(response => {
            if (response.ok) {
                jQuery("#newAuthorModal").modal("hide");

                response.json().then(author => {
                    console.log(author);
                    var newOption = jQuery('<option></option>').attr('value', author.id).text(author.fullname);
                    jQuery('#author').append(newOption);
                });

            } else {
                throw new Error(response.status);
            }
        }).catch(err => {
            console.log(err);
        })

    } else {
        form.classList.add('was-validated');
    }
}
