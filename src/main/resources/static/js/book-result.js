function validateFormPrice() {

    var minPrice = document.getElementById("price-min-control").value;
    var maxPrice = document.getElementById("price-max-control").value;
    if (minPrice === "" || maxPrice === "") {
        return false; // Ngăn chặn gửi biểu mẫu
    }
    else {
        if (Number(minPrice) >= Number(maxPrice))
            return false;
    }
    return true; // Cho phép gửi biểu mẫu
}

function scrollToTop() {
    // Cuộn lên trên trong 500ms
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}