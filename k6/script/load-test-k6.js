import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
    stages: [
        { duration: '2m', target: 100 }, // ramp up to 100 users
        { duration: '5m', target: 100 }, // stay at 100 users for 5 minutes
        { duration: '2m', target: 0 },    // ramp down to 0 users
    ],
};

export default function () {
    let response = http.get('https://ruqvuimgf2.execute-api.us-east-1.amazonaws.com/beta/pedidos');
    check(response, { 'status was 200': (r) => r.status == 200 });
    sleep(1);
}
