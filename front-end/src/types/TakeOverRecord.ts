export default interface TakeOverRecord {
  id: string;
  equipment_id: string;
  username: string|null;
  take_over_time: string;
  status: string;
  verifier: string|null;
  take_over_person: string|null;
  type_take_over: string;
  message: string|null;
  cost: string|null;
  created_by: string;
  created_time: string;
  updated_by: string;
  updated_time: string;
  metadata_info: Object;
  device_id: string,
  name: string,
  take_back_status : string|null
}