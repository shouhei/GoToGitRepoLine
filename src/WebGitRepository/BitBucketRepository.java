package WebGitRepository;

public class BitBucketRepository extends WebGitRepository{
    BitBucketRepository(String remote,  String revision, String path, String line) {
        super(remote, revision, path, line);
    }
    public String url() {
        // https://bitbucket.org/atlassian/amps/src/9bcc15b9b0d2bbe623801f6064d39a3dbef759ca/sdk-installer/pom.xml?at=6.3-stable&fileviewer=file-view-default#pom.xml-6
        // https:/bitbucket.org/atlassian/amps/src/sdk-installer/pom.xml/9bcc15b9b0d2bbe623801f6064d39a3dbef759ca?fileviewer=file-view-default#9bcc15b9b0d2bbe623801f6064d39a3dbef759ca-14
        String []dir_or_file = this.path.split("/");
        return this.remote + "/src/" + this.revision + this.path +"?fileviewer=file-view-default#" + dir_or_file[dir_or_file.length - 1]+ "-" + this.line;
    }
}
