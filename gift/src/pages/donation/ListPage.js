import { useSearchParams } from "react-router-dom";
import ListComponent from "../../components/donation/ListComponent";

const ListPage = () => {
  const [queryParams] = useSearchParams();

  const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1;
  const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10;

  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">
        기부 게시판 목록
        {page} --- {size}
      </div>
      <ListComponent />
    </div>
  );
};

export default ListPage;
