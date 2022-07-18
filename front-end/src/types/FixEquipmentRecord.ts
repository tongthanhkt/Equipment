export default interface FixEquipmentRecord {
  id: string;
  equipment_id: string;
  device_id: string;
  equipment_name: string;
  fixer: string|null;
  fixing_time: string;
  status: string;
  message: string | null;
  cost: string | null;
  created_by: string;
  created_time: string;
  updated_by: string;
  updated_time: string;
  metadata_info: Object;
  take_over_status: string;

  name: string;
}
