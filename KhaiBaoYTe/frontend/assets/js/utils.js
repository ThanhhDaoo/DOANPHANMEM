/* ==== Toast ==== */
function ensureToastRegion(){
  let r = document.getElementById('toast-region');
  if(!r){ r = document.createElement('div'); r.id = 'toast-region'; document.body.appendChild(r); }
  return r;
}
function toast(msg, type='info'){
  const region = ensureToastRegion();
  const el = document.createElement('div');
  el.className = 'toast ' + type;
  el.textContent = msg;
  region.appendChild(el);
  setTimeout(() => el.remove(), 3500);
}

/* ==== Modal ==== */
function modal({ title='', body='', okText='Lưu', cancelText='Huỷ', onOk }){
  const wrap = document.createElement('div');
  wrap.className = 'modal-backdrop';
  wrap.innerHTML = `
    <div class="modal" role="dialog" aria-modal="true">
      <div class="modal-header">
        <h3 class="card-title">${title}</h3>
        <button class="btn btn-ghost btn-sm" data-close>✕</button>
      </div>
      <div class="modal-body">${body}</div>
      <div class="modal-footer">
        <button class="btn btn-outline" data-close>${cancelText}</button>
        <button class="btn btn-primary" data-ok>${okText}</button>
      </div>
    </div>`;
  document.body.appendChild(wrap);
  const close = () => wrap.remove();
  wrap.addEventListener('click', e => { if(e.target === wrap || e.target.dataset.close !== undefined) close(); });
  wrap.querySelector('[data-ok]').addEventListener('click', () => {
    const ok = onOk?.(wrap);
    if(ok !== false) close();
  });
  return wrap;
}

/* ==== Format ==== */
function fmtDate(d){
  if(!d) return '';
  const x = new Date(d);
  return x.toLocaleDateString('vi-VN');
}
function fmtDateTime(d){
  if(!d) return '';
  const x = new Date(d);
  return x.toLocaleString('vi-VN', { hour:'2-digit', minute:'2-digit', day:'2-digit', month:'2-digit', year:'numeric' });
}
function debounce(fn, wait=250){
  let t; return (...args) => { clearTimeout(t); t = setTimeout(() => fn(...args), wait); };
}

window.toast = toast;
window.modal = modal;
window.fmtDate = fmtDate;
window.fmtDateTime = fmtDateTime;
window.debounce = debounce;
