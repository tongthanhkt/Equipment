<template>
  <div class="relative bg-white shadow rounded-3xl sm:p-10 w-4/6">
    <div>
      <div class="flex items-center space-x-5">
        <div class="block pl-2 font-semibold text-xl self-start text-gray-700">
          <h1 class="text-2xl leading-relaxed">Chỉnh sửa thiết bị</h1>
        </div>
      </div>
      <div class="divide-y divide-gray-200">
        <div
          class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
        >
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Mã thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tên thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
          </div>
          <div class="flex flex-col">
            <label class="leading-loose">Danh mục</label>
            <select
              id="country"
              name="country"
              autocomplete="country-name"
              class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              <option>United States</option>
              <option>Canada</option>
              <option>Mexico</option>
            </select>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Tình trạng mới nhập</label>
              <select
                id="country"
                name="country"
                autocomplete="country-name"
                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              >
                <option>United States</option>
                <option>Canada</option>
                <option>Mexico</option>
              </select>
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Trạng thái thiết bị</label>
              <input
                type="text"
                class="mt-1 block w-full py-2 px-3 w-64 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder=""
              />
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Giá tiền</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tháng / Năm</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Thời gian khấu hao</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Thời gian nhập thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Giá trị khấu hao</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Tệp đính kèm </label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
          </div>
        </div>

        <div class="pt-4 flex items-center space-x-4">
          <button
            class="flex justify-center items-center w-48 text-gray-900 px-4 py-3 rounded-md focus:outline-none"
          >
            <svg
              class="w-6 h-6 mr-3"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
            Hủy
          </button>
          <button
            class="bg-blue-500 flex justify-center w-48 items-center text-white px-4 py-3 rounded-md focus:outline-none"
          >
            Thêm
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
