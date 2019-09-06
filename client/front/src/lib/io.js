// request

const io = {
    request(method, params = {}) {
        return request.query(method, JSON.stringify(params))

    },
    receive() {

    },
    requestAsync(method, params = {}) {
        let promise = new Promise((resolve,reject)=>{

        })
    }
}


export default io

