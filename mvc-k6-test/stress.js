import { URLSearchParams } from 'https://jslib.k6.io/url/1.0.0/index.js';
import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

export let options = {

  stages: [
    { duration: '1m', target: 4 },
    { duration: '1m', target: 6 },
    { duration: '5m', target: 10 },
    { duration: '5m', target: 20 },
    { duration: '5m', target: 40 },
    { duration: '5m', target: 80 },
    { duration: '5m', target: 120 },
  ],

  thresholds: {
    http_req_duration: ['p(99)<600'], // 99% of requests must complete below 1.5s
  },
};

const BASE_URL = 'http://localhost:8080';
const params = {
  headers: {
    'Content-Type': 'application/json',
  },
};

const data = { code: 'class Solution {\n' +
      '\tpublic int[] solution(int brown, int yellow) {\n' +
      '\t\tfor (int x = 1; x < (brown + 4) / 2; x++) {\n' +
      '\t\t\tint y = (brown + 4) / 2 - x;\n' +
      '\t\t\tif ((x - 2) * (y - 2) == yellow) {\n' +
      '\t\t\t\tif (x > y) {\n' +
      '\t\t\t\t\treturn new int[] {x, y};\n' +
      '\t\t\t\t}\n' +
      '\t\t\t\treturn new int[] {y, x};\n' +
      '\t\t\t}\n' +
      '\t\t}\n' +
      '\t\tint[] answer = {-1, -1};\n' +
      '\t\treturn answer;\n' +
      '\t}\n' +
      '}' };

export default function ()  {
  // Request Execute Code
  let executeCode = http.post(`${BASE_URL}/code`, JSON.stringify(data), params);
  check(executeCode, {"Execute Code API status check" : (resp) => resp.status === 200});

  let requestId = executeCode.body;
  console.log(requestId);

  const searchParams = new URLSearchParams([
    ['requestId', requestId],
  ]);

  let getResult = http.get(`${BASE_URL}/code?${searchParams.toString()}`);
  check(getResult, {"Get Code Execution Result" : (resp) => resp.status === 200});

  while (getResult.body === 'false'){
    console.log('retry');
    getResult = http.get(`${BASE_URL}/code?${searchParams.toString()}`);
    sleep(2);
  }
};
