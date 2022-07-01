export default interface Equipment {
  category_id: string;
  category_name: string | null;
  created_by: String;
  created_time: String;
  device_id: String | null;
  id: String;
  import_date: string;
  name: String | null;
  price: string;
  start_status: String | null;
  take_over_person_id: String | null;
  take_over_person_name: String | null;
  take_over_status: String | null;
  updated_by: string;
  updated_time: string;
  device_status: String | null;
  depreciated_value: String | null;
  depreciation_period: String | null;
  period_type: String | null;
  metadata_info: Object | null;
}
