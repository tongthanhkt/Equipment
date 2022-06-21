export default interface TakeOverRecord {
  id: string;
  equipment_id: string;
  username: string;
  take_over_time: string;
  status: string;
  verifier: string;
  take_over_person: string;
  type: string;
  message: string;
  cost: string;
  created_by: string;
  created_time: string;
  updated_by: string;
  updated_time: string;
  metadata_info: Object;
}