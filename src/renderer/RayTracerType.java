package renderer;

/**
 * Ray tracer types.
 * <p>
 * Anti-aliasing / super-sampling is <em>not</em> a ray tracer type: it is a
 * camera-side sampling concern configured through
 * {@link Camera.Builder#setAntiAliasing(int)} and
 * {@link Camera.Builder#setAdaptiveSuperSampling(int)}.
 */
public enum RayTracerType {
	/** Simple (basic) recursive ray tracer. */
	SIMPLE
}
