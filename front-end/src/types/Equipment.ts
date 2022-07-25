export default interface Equipment {
	category_id: string | null;
	category_name: string | null;
	created_by: string | null;
	created_time: string | null;
	device_id: string | null;
	id: string | null;
	import_date: string | null;
	name: string | null;
	price: string | null;
	start_status: string | null;
	take_over_person_id: string | null;
	take_over_status: string | null;
	updated_by: string | null;
	updated_time: string | null;
	device_status: string | null;
	depreciated_value: string | null;
	depreciation_period: string | null;
	period_type: string | null;
	compensation_status: string | null;
	metadata_info: Object | null;
}
