class Verifier {
  private static instance: Verifier | null = null;

  private constructor() {}

  public static getInstance(): Verifier {
    if (this.instance === null) {
      this.instance = new Verifier();
    }
    return this.instance;
  }

  public verify(token: string): Promise<any> {
    return new Promise((resolve, reject) => {
      // 인가 기능 미구현
      const is_valid = true;

      if (is_valid) {
        resolve(true);
      } else {
        reject(new Error());
      }
    });
  }
}

export default Verifier.getInstance();
