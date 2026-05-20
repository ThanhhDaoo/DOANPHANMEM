/**
 * Inject header/footer cho các trang public (khách vãng lai).
 * Dùng: PublicLayout.mount('home' | 'news' | 'guide')
 */
const PublicLayout = {
  mount(active){
    const u = Auth.user();
    const isLogged = !!u;
    const homeHref = isLogged ? Auth.homeFor(u.role) : '/frontend/index.html';

    const header = document.createElement('header');
    header.className = 'public-header';
    header.innerHTML = `
      <a href="/frontend/index.html" class="brand">
        <div class="logo">YT</div>
        <div>
          <div>Khai báo Y tế</div>
          <div class="text-xs text-muted" style="font-weight:400">Phường Thanh Khê</div>
        </div>
      </a>
      <nav>
        <a href="/frontend/index.html" data-key="home">Trang chủ</a>
        <a href="/frontend/pages/public/news.html" data-key="news">Tin tức</a>
        <a href="/frontend/pages/public/guide.html" data-key="guide">Hướng dẫn</a>
        <a href="/frontend/pages/public/contact.html" data-key="contact">Liên hệ</a>
      </nav>
      <div class="actions">
        ${isLogged
          ? `<a class="btn btn-outline btn-sm" href="${homeHref}">Khu vực của tôi</a>
             <button class="btn btn-ghost btn-sm" id="pl-logout">Đăng xuất</button>`
          : `<a class="btn btn-ghost btn-sm" href="/frontend/pages/auth/login.html">Đăng nhập</a>
             <a class="btn btn-primary btn-sm" href="/frontend/pages/auth/register.html">Đăng ký</a>`}
      </div>
    `;

    const footer = document.createElement('footer');
    footer.className = 'public-footer';
    footer.innerHTML = `
      <div>© 2026 UBND Phường Thanh Khê - Hệ thống Khai báo Y tế</div>
      <div class="text-xs mt-2">Đồ án phần mềm - Nhóm 02</div>
    `;

    document.body.prepend(header);
    document.body.appendChild(footer);

    if(active){
      header.querySelector(`nav a[data-key="${active}"]`)?.classList.add('active');
    }
    document.getElementById('pl-logout')?.addEventListener('click', () => Auth.logout());
  },
};
window.PublicLayout = PublicLayout;
