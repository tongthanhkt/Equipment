export default interface TakeOverRecord {
  id: string;
  equipment_id: String;
  username: string;
  take_over_time: String;
  status: String;
  verifier: String;
  take_over_person: String;
  type: String;
  message: String;
  cost: string;
  created_by: String;
  created_time: String;
  updated_by: String;
  updated_time: String;
  metadata_info: Object;
}