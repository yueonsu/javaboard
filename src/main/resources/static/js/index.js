const regexp = {
    name : /^([가-힣]{2,5})$/,
    id : /^([a-zA-Z0-9]{5,20})$/,
    password : /^(?=.*[a-zA-Z])(?=.*[_~!@#])(?=.*[0-9]).{6,20}$/,
    email : /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
    isCheck : function (target, val) {
        return this[target].test(val);
    },
    makeMsg : function (msg, classname, elem) {
        const div = document.createElement('div');
        div.classList.add(classname);
        div.innerHTML = `
            <strong>${msg}</strong>
        `;
        elem.after(div);
    },
    delMsg : function (classname) {
        const isMsg = document.querySelector(`.${classname}`);
        if(isMsg) {
            isMsg.remove();
        }
    }
}

const myFetch = {
    send : function (fetchObj, cb) {
        return fetchObj
            .then(res => res.json())
            .then(cb)
            .catch(e => {console.error(e)});
    },
    get : function (url, cb, param) {
        if(param) {
            const queryString = '?' + Object.keys(param).map(key => `${key}=${param[key]}`).join("&");
            url += queryString;
        }
        return this.send(fetch(url), cb);
    },
    post : function (url, cb, param) {
        return this.send(fetch(url, {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(param)
        }), cb);
    }
}