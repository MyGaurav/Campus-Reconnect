let page = 0;
let loading = false;
const feed = document.getElementById("feed");
const searchInput = document.getElementById("searchInput");
const categoryFilter = document.getElementById("categoryFilter");

async function loadPosts(reset = false) {
    if (loading) return;
    loading = true;

    let url = `/api/posts?page=${page}`;
    if (searchInput.value.trim() !== "") url = `/api/posts/search?keyword=${searchInput.value}&page=${page}`;
    if (categoryFilter.value !== "") url = `/api/posts/category?category=${categoryFilter.value}&page=${page}`;

    const response = await axios.get(url);
    const posts = response.data.content;

    if (reset) feed.innerHTML = "";

    posts.forEach(post => {
        feed.innerHTML += `
        <div class="post-card">
            <h3>${post.title}</h3>
            <p>${post.description}</p>
            ${post.imageUrl ? `<img src="${post.imageUrl}" />` : ""}
        </div>`;
    });

    if (!response.data.last) {
        page++;
        loading = false;
    }
}

window.addEventListener("scroll", () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 300) {
        loadPosts();
    }
});

searchInput.addEventListener("input", () => { page = 0; loadPosts(true); });
categoryFilter.addEventListener("change", () => { page = 0; loadPosts(true); });

loadPosts();
