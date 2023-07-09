

function addToCart() {

    var img = document.getElementsByClassName("img-info")[0].src;
    var id = document.getElementById("id").textContent;
    var title = document.getElementById("title").textContent;
    var price = document.getElementById("price").textContent;
    var quantity = document.getElementById("quantity").value;
    stringCartItems = localStorage.getItem("cartItems");
    var cartItems = [];
    newItem = { id, img, title, price, quantity };
    if (stringCartItems != null) {
        cartItems = JSON.parse(stringCartItems);
        item = cartItems.find(item => item.id == id);
        if (item != null) {

            item.quantity = parseInt(quantity) + parseInt(item.quantity);
        } else {
            item = newItem
            cartItems.push(item);
        }

    } else {
        cartItems.push(newItem);
    }
    localStorage.setItem("cartItems", JSON.stringify(cartItems))
}

function removeItem(id) {

    var cartItems = JSON.parse(localStorage.getItem("cartItems"));

    // Lọc ra các mục khác với ID được chỉ định
    var updatedCartItems = cartItems.filter(function (item) {
        return item.id != id;
    });
    if (updatedCartItems.length == 0 || updatedCartItems == null) {
        alert('adi');
        removeAll();
    }
    else {
        localStorage.setItem("cartItems", JSON.stringify(updatedCartItems));
    }
    loadShopingCart();

}
function removeAll() {
    localStorage.removeItem("cartItems");  //item
}
function loadShopingCart() {
    var cartItems = JSON.parse(localStorage.getItem("cartItems"));
    var html = "";
    if (cartItems != null) {

        cartItems.forEach(item => {
            html += `<div class="row border-top border-bottom">
        <div class="row main align-items-center">
            <div class="col-1"><input type="checkbox" name="choose" ></div>
            <div class="col-2"><img class="img-fluid" src="`+ item.img + `"></div>
            <div class="col">
                <div class="row text-muted">`+ item.title + `</div>
                <div class="row">`+ item.price + `</div>
            </div>
            <div class="col">
                <a href="#">-</a><a href="#" class="border">`+ item.quantity + `</a><a href="#">+</a>
            </div>
            <div class="col">&euro; `+ parseFloat(item.price) * parseFloat(item.quantity) + `       <a onclick="removeItem(` + item.id + `)"> 
            <span class="close"> &#10005;</span>
            </a>
            </div>
        </div>
        </div>`});

    }
    else
        html = "Không có sản phẩm nào trong giỏ hàng. Hãy quay lại để mua sắm!"
    document.getElementById("listCartItems").innerHTML = html;
    document.getElementById("quantity-items").innerHTML = cartItems.length + ` items`;
}




function saveToStorage(key, value) {
    var data = value;

    // Kiểm tra, nếu dữ liệu là kiểu mảng ARRAY
    // -> cần phải convert về kiểu String để lưu trữ được vào LocalStorage
    if (Array.isArray(value)) {

        // Hàm JSON.stringify sẽ chuyển đổi ARRAY thành STRING
        data = JSON.stringify(value);
    }

    // Lưu trữ vào LocalStorage với từ khóa "key"
    window.localStorage.setItem(key, data);
}

/**
 * Hàm lấy giá trị được lưu trữ trong LocalStorage
 * @param {*} key Từ khóa
 */
function getFromStorage(key) {

    // Lấy dữ liệu từ LocalStorage
    return window.localStorage.getItem(key);
}