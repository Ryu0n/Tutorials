import requests, json


class Base:
    db = None
    attr = []

    res = None
    json_dict = None

    def ask_api(self):
        print('0. create()')
        print('1. get_all()')
        print('2. get_response_by_no()')
        print('3. update()\n')

        api_call = int(input('Choose API what you want to call : '))

        if api_call == 0:
            self.res = self.call_api(callback=self.api_create)
        if api_call == 1:
            self.res = self.call_api(callback=self.api_get_all)  # JsonResponse
        elif api_call == 2:
            self.res = self.call_api(callback=self.api_get_response_by_no)  # JsonResponse
        elif api_call == 3:
            self.res = self.call_api(callback=self.api_update)  # HttpResponse

        if self.res is not None:
            print(self.res)

    def call_api(self, callback):
        return callback()

    def api_create(self):
        url = 'http://127.0.0.1:8000/rest_api/c/{0}/'.format(self.db)
        data = {
            'no': 9999,
            'duplicate': 11,
            'div_1': '0',
            'div_2': '0',
            'div_3': '0',
            'div_4': '0',
            'file_name': '0',
            'file_dir': '0',
            'value': '0'
        }
        response = requests.post(url, json=data)
        return response

    def api_get_all(self):
        url = 'http://127.0.0.1:8000/rest_api/r/{0}/1/'.format(self.db)
        response = requests.get(url)
        return response.text

    def api_get_response_by_no(self):
        # id에서 no으로 대체해야함. 순서가 아닌 데이터 값으로 검색 (2020-11-20)
        id = int(input('\nplease input id : '))
        url = 'http://127.0.0.1:8000/rest_api/r/{0}/0/?id={1}'.format(self.db, id)
        response = requests.get(url)
        return response.text

    def api_update(self):
        no = int(input('\nplease input number : '))
        url = 'http://127.0.0.1:8000/rest_api/u/{0}/{1}/?'.format(self.db, no)
        qs = ''

        for a in self.attr:
            check = input('{0} change? (y/n) : '.format(a))
            if check == 'y':
                v = input('input value : ')
                qs += a + '=' + v + '&'

        qs = qs[:-1]
        url += qs
        response = requests.get(url)
        return response.text
