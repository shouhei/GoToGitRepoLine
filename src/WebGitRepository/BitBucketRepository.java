package WebGitRepository;

public class BitBucketRepository extends WebGitRepository{
    BitBucketRepository(String remote,  String revision, String path, String line) {
        super(remote, revision, path, line);
    }
    public String url() {
        String []dir_or_file = this.path.split("/");
        return this.remote + "/src/" + this.revision + this.path +"?fileviewer=file-view-default#" + dir_or_file[dir_or_file.length - 1]+ "-" + this.line;
    }
}
