function postComment() {
    // Lấy nội dung bình luận từ ô văn bản
    var comment = document.getElementById("textAreaExample").value;
    if (comment != null) {
        bookId = Number.parseInt(jQuery("#id").text());
        commentRequest = { comment: comment, bookId: bookId };
        console.log(commentRequest);
        fetch('/bookshop/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentRequest)
        })
            .then(response => response.json())
            .then(response => {
                // if (response.ok) {
                if (response != null) {

                    html = ` <div>
                    <hr />
                    <div class="row">
                        <div class="col-sm-3">
                            <img src="https://cdn.chanhtuoi.com/uploads/2022/01/avatar-tet.jpg"
                                style="height: 60px ; width: 60px; " class="img-rounded">
                            <div class="review-block-name"><a href="#">`+ response.userFullname + `</a>
                            </div>
                            <div class="review-block-date">
                                <span >`+ response.createdDate + `
                              
                            </div>
                        </div>
                        <div class="col-sm-9">

                            <div class="review-block-description">
                                <span >`+ response.comment + ` </span>
                            </div>
                        </div>
                    </div>
                </div>`


                    document.getElementById("textAreaExample").value = '';
                    // document.getElementById("comment").innerHTML += html;




                    var content = jQuery("#comment").html();
                    jQuery("#comment").html(html + content);
                    console.log('Bình luận đã được đăng thành công');
                }

                else {
                    console.log('Đăng bình luận thất bại');
                }
            })
            .catch(error => {
                console.error('Lỗi khi gửi yêu cầu POST:', error);
            });
    }
}

// Lắng nghe sự kiện khi người dùng nhấp vào tab xem trước
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

function loadData(id) {
    fetch('http://localhost:8080/bookshop/books/booksByCategory/' + id).then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        document.getElementById("booksByCategory").innerHTML = html;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });

    fetch('http://localhost:8080/bookshop/books/booksByAuthor/' + id).then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        document.getElementById("booksByAuthor").innerHTML = html;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });
}
function getBooksByCategory(id, page) {
    // this.event.preventDefault();
    fetch('http://localhost:8080/bookshop/books/booksByCategory/' + id + "?page=" + page).then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        var booksByCategoryElement = document.getElementById("booksByCategory");
        booksByCategoryElement.innerHTML = html;
        // document.getElementById("booksByCategory").innerHTML = html;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });
}
function getBooksByAuthor(id, page) {
    // this.event.preventDefault();
    fetch('http://localhost:8080/bookshop/books/booksByAuthor/' + id + "?page=" + page).then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        var booksByCategoryElement = document.getElementById("booksByAuthor");
        booksByCategoryElement.innerHTML = html;
        // document.getElementById("booksByCategory").innerHTML = html;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });
}

